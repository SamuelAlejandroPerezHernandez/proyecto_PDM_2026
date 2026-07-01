const express = require('express')
const router = express.Router()
const noteController = require('../controllers/note.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.post('/', verificarToken, noteController.addNote)

router.get('/', verificarToken, noteController.getNotesList)

router.get('/:id', verificarToken, noteController.getNoteDetail)

module.exports = router