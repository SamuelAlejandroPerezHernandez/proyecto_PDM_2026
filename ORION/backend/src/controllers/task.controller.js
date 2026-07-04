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
            due_date
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

const updateTask = async(req, res) => {
    const {id} = req.params

    const { id: taskId, user_id, category_id, ...bodyCompleto } = req.body

    const camposAActualizar = {}
    Object.keys(bodyCompleto).forEach(key => {
        if (bodyCompleto[key] !== null && bodyCompleto[key] !== undefined) {
            camposAActualizar[key] = bodyCompleto[key]
        }
    })

    const {data: updateTask, error} = await supabase
        .from('task')
        .update(camposAActualizar)
        .eq('id', id)
        .select()
        .single()

        if(error){
            return res.status(500).json({message: 'Error al intentar actualizar la tarea'})
        }

        res.status(200).json({
            updateTask
        })
}

const deleteTask = async(req, res) => {
    const {id} = req.params

    const { data: deleteTask, error } = await supabase
        .from('task')
        .delete()
        .eq('id', id)
        .select()
        .single()

    if(error){
            return res.status(500).json({message: 'Error al intentar eliminar la tarea'})
        }

    res.status(200).json({
        deleteTask
    })
}


module.exports = { addTask, getTasksList, getTaskDetail, updateTask, deleteTask }

