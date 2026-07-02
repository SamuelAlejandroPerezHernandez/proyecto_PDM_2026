const express = require('express')
const router = express.Router()
const taskController = require('../controllers/task.controller')
const { verificarToken }  = require('../middlewares/auth.middleware')

router.post('/postTask', verificarToken , taskController.addTask)
router.get('/getTask', verificarToken , taskController.getTasksList)
router.get('/upcoming', verificarToken , taskController.getUpcomingTasks)
router.get('/:id', verificarToken , taskController.getTaskDetail)

module.exports = router