const express = require('express')
const router = express.Router()
const taskController = require('../controllers/task.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.post('/postTask', verificarToken , taskController.addTask)

router.get('/getTask', verificarToken , taskController.getTasksList)

router.get('/detail/:id', verificarToken , taskController.getTaskDetail)

router.put('/update/:id', verificarToken , taskController.updateTask)

router.delete('/delete/:id', verificarToken , taskController.deleteTask)

module.exports = router