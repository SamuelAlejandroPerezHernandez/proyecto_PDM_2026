const supabase = require('../config/supabase');

const getProfile = async(req, res) => {
    const userId = req.user.id;

    const {data: perfil, error} = await supabase
        .from('profiles')
        .select('email')
        .eq('user_id', userId)
        .single();

    if(error){
        return res.status(500).json({message: 'Error al obtener el perfil'});
    }

    if(!perfil){
        return res.status(404).json({message: 'Perfil no encontrado'});
    }

    res.status(200).json({email: perfil.email});
};

module.exports = { getProfile };