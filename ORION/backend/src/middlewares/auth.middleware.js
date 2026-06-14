const jwt = require('jsonwebtoken')

const verificarToken = (req, res, next) => {
    const authHeader = req.headers['authorization']

    if(!authHeader){
        return res.status(401).json({message: 'No se encontro un header'})
    }

    const token = authHeader.split(' ')[1]

    if(!token){
        return res.status(401).json({message: 'No se encontro un token'})
    }

    try{
        const decoded =jwt.verify(token, process.env.JWT_SECRET)
        req.user = decoded
        next()
    }catch(error){
        return res.status(401).json({message: 'Token falso o expirado'})
    }
}

module.exports = { verificarToken }

