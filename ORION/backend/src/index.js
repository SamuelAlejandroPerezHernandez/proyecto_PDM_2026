const express = require('express')
const cors = require('cors')
const authRoutes = require('./routes/auth.routes')
const tasksRoutes = require('./routes/task.routes')
const categoriesRoutes = require('./routes/categories.routes')
require('dotenv').config()

const app = express()

const Port = process.env.PORT || 3000

// pa mi yo del futuro por si se me olvida, lo que sigue son los middelwares carck un saludo

app.use(cors()) //seguridad brou
app.use(express.json()) //permite leer json

// mi pana aqui va la primera ruta pa

app.get('/', (req, res) => {
    res.json({ message: 'ORION API funcionando' })
})

app.use('/api/auth', authRoutes)

app.use('/api/tasks', tasksRoutes)

app.use('/api/categories', categoriesRoutes)

// ahora haremos el listenen que es para levantar el servidor crack

app.listen(Port, () => {
    console.log(`Servidor corriendo en el puerto ${Port}`)
})