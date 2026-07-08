const express = require('express');
const router = express.Router();
const { getProfile } = require('../controllers/perfil.controller');
const { verificarToken }  = require('../middlewares/auth.middleware')

router.get('/getPerfil', verificarToken, getProfile);

module.exports = router;