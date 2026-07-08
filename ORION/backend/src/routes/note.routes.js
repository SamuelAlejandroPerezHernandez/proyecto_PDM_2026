const express = require('express')
const router = express.Router()
const noteController = require('../controllers/note.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.post('/postNotes', verificarToken, noteController.addNote)

router.get('/getNotes', verificarToken, noteController.getNotesList)

router.get('/recent', verificarToken, noteController.getRecentNotes)

router.get('/detail/:id', verificarToken, noteController.getNoteDetail)

router.put('/update/:id', verificarToken, noteController.updateNote)

router.delete('/delete/:id', verificarToken, noteController.deleteNote)

module.exports = router