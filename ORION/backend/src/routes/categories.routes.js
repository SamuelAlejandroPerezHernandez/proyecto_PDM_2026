const express = require('express')
const router = express.Router()
const categoriesController = require('../controllers/categories.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.get('/getCategories', verificarToken, categoriesController.getCategories)

module.exports = router





