const supabase = require('../config/supabase');

const addTask = async (req, res) => {
    try {
        const { title, description, due_date, due_time } = req.body;
        const userId = req.user.id;
        const categoryId = req.body.category_id;

        if (!title) {
            return res.status(400).json({ message: 'El titulo es requerido' });
        }

        const { data: newTask, error } = await supabase
            .from('task')
            .insert({
                user_id: userId,
                category_id: categoryId,
                title,
                description,
                due_date
            })
            .select()
            .single();

        if (error) {
            console.error('🚨 ERROR SUPABASE ADD_TASK:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al intentar agregar la tarea', detalle: error.message });
        }

        return res.status(201).json({ newTask });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

const getTasksList = async (req, res) => {
    try {
        const { data: tasksList, error } = await supabase
            .from('task')
            .select('*')
            .eq('user_id', req.user.id);

        if (error) {
            console.error('🚨 ERROR SUPABASE GET_TASKS:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al intentar obtener las tareas ingresadas', detalle: error.message });
        }

        return res.status(200).json({ tasksList });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

const getTaskDetail = async (req, res) => {
    try {
        const { id } = req.params;
        const { data: taskDetail, error } = await supabase
            .from('task')
            .select('*')
            .eq('id', id)
            .single();

        if (error) {
            console.error('🚨 ERROR SUPABASE TASK_DETAIL:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al intentar filtrar una tarea en especifico', detalle: error.message });
        }

        return res.status(200).json({ taskDetail });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

const updateTask = async (req, res) => {
    try {
        const { id } = req.params;
        const { id: taskId, user_id, category_id, ...bodyCompleto } = req.body;

        const camposAActualizar = {};
        Object.keys(bodyCompleto).forEach(key => {
            if (bodyCompleto[key] !== null && bodyCompleto[key] !== undefined) {
                camposAActualizar[key] = bodyCompleto[key];
            }
        });

        const { error } = await supabase
            .from('task')
            .update(camposAActualizar)
            .eq('id', id)
            .select()
            .single();

        if (error) {
            console.error('🚨 ERROR SUPABASE UPDATE_TASK:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al intentar actualizar la tarea', detalle: error.message });
        }

        return res.status(204).send();
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

const deleteTask = async (req, res) => {
    try {
        const { id } = req.params;

        const { error } = await supabase
            .from('task')
            .delete()
            .eq('id', id)
            .select()
            .single();

        if (error) {
            console.error('🚨 ERROR SUPABASE DELETE_TASK:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al intentar eliminar la tarea', detalle: error.message });
        }

        return res.status(204).send();
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

const getUpcomingTasks = async (req, res) => {
    try {
        const today = new Date().toISOString().split('T')[0];
        const { data: upcomingTasks, error } = await supabase
            .from('task')
            .select('*')
            .eq('user_id', req.user.id)
            .gte('due_date', today)
            .order('due_date', { ascending: true })
            .limit(3);

        if (error) {
            console.error('🚨 ERROR SUPABASE UPCOMING_TASKS:', JSON.stringify(error, null, 2));
            return res.status(500).json({ message: 'Error al obtener las tareas urgentes', detalle: error.message });
        }

        return res.status(200).json({ upcomingTasks });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

module.exports = { addTask, getTasksList, getTaskDetail, updateTask, deleteTask, getUpcomingTasks };