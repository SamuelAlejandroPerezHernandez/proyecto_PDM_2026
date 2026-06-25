const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const supabase = require('../config/supabase');

const register = async(req, res) => {
    const {email, password} = req.body;

    if(!email || !password){
        return res.status(400).json({message: 'Email y contraseña son requeridos'});
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if(!emailRegex.test(email)){
        return res.status(400).json({message: 'El formato del correo electrónico no es válido'});
    }

    const {data: existeUsuario, error} = await supabase
        .from('users')
        .select('id')
        .eq('email', email)
        .single();

        if(error && error.code !== 'PGRST116'){
            return res.status(500).json({message: 'Error al verificar el usuario'});
        }

        if(existeUsuario){
            return res.status(400).json({message: 'Este correo ya esta autenticado'});
        }

    const password_hash = await bcrypt.hash(password, 10);

    const {data: nuevoUsuario, error: errorInsert} = await supabase
        .from('users')
        .insert({email, password_hash})
        .select()
        .single();

        if(errorInsert){
            return res.status(500).json({message: 'Error al regestriar el usuario'});
        }

    const {data: nuevoPerfil, error: errorPerfil} = await supabase
        .from('profiles')
        .insert({user_id: nuevoUsuario.id, email})
        .select()
        .single();

        if(errorPerfil){
            return res.status(500).json({message: 'Error al iniciar el perfil del usuario registrado'});
        }

    const token = jwt.sign(
        {id: nuevoUsuario.id, email: nuevoUsuario.email},
        process.env.JWT_SECRET,
        {expiresIn: '30d'}
    )

    res.status(201).json({
        token,
        id: nuevoUsuario.id,
        email: nuevoUsuario.email
    })
        
}

const login = async(req, res) => {
    const {email, password} = req.body;

    if(!email || !password){
        return res.status(400).json({message: 'Email y contraseña no puede estar vacios'});
    }

    const {data: existeusuario, error} = await supabase
        .from('users')
        .select('*')
        .eq('email', email)
        .single();

        if(!existeusuario){
            return res.status(400).json({message: 'Usuario no encontrado'})
        }

        if(error){
            return res.status(500).json({message: 'Error al verificar el usuario'});
        }

    const verificarContraseña = await bcrypt.compare(password, existeusuario.password_hash);

    if(!verificarContraseña){
        return res.status(400).json({message: 'Contraseña incorrecta'})
    }

    const token = jwt.sign(
        {id: existeusuario.id, email: existeusuario.email},
        process.env.JWT_SECRET,
        {expiresIn: '30d'}
    )

    res.status(200).json({
        token,
        id: existeusuario.id,
        email: existeusuario.email
    })
}

module.exports = { register, login }