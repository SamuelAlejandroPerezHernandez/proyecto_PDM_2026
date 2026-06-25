const express = require('express')
const router = express.Router()
const noteController = require('../controllers/note.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.post('/notes', verificarToken, noteController.addNote)

router.get('/notes', verificarToken, noteController.getNotesList)

router.get('/notes/:id', verificarToken, noteController.getNoteDetail)

module.exports = router