const supabase = require('../config/supabase')

const addTask = async(req, res) => {
    const {title, description, due_date, due_time} = req.body

    const userId = req.user.id
    const categoryId = req.body.category_id

    if(!title){
        return res.status(400).json({message: 'El titulo es requerido'})
    }

    const {data: newTask, error} = await supabase
        .from('task')
        .insert({
            user_id: userId,
            category_id: categoryId,
            title,
            description,
            due_date,
            due_time
        })
        .select()
        .single()

        if(error){
            return res.status(500).json({message: 'Error al intentar agregar la tarea'})
        }

    res.status(201).json({
        newTask
    })
}

const getTasksList = async(req, res) => {
    const {data: tasksList, error} = await supabase
        .from('task')
        .select('*')
        .eq('user_id', req.user.id)

    if(error){
        return res.status(500).json({message: 'Error al intentar obtener las tareas ingresadas'})
    }

    res.status(200).json({
        tasksList
    })
}

const getTaskDetail = async(req, res) => {
    const {id} = req.params

    const {data: taskDetail, error} = await supabase
        .from('task')
        .select('*')
        .eq('id', id)
        .single()

        if(error){
            return res.status(500).json({message: 'Error al intentar filtrar una tarea en especifico'})
        }

        res.status(200).json({
            taskDetail
    })
}

module.exports = { addTask, getTasksList, getTaskDetail }

