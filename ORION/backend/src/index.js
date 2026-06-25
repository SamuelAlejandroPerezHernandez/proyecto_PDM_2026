const express = require('express')
const cors = require('cors')
const authRoutes = require('./routes/auth.routes')
const tasksRoutes = require('./routes/task.routes')
const noteRoutes = require('./routes/note.routes')  
const categoriesRoutes = require('./routes/categories.routes')
require('dotenv').config()

const app = express()

const Port = process.env.PORT || 3000

app.use(cors())
app.use(express.json())

app.get('/', (req, res) => {
    res.json({ message: 'ORION API funcionando' })
})

app.use('/api/auth', authRoutes)

app.use('/api/tasks', tasksRoutes)

app.use('/api/notes', noteRoutes)

app.use('/api/categories', categoriesRoutes)

app.listen(Port, () => {
    console.log(`Servidor corriendo en el puerto ${Port}`)
})