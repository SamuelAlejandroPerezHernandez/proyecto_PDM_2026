const supabase = require('../config/supabase');

const addTask = async (req, res) => {
    try {
        const { title, description, due_date } = req.body;
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
            return res.status(500).json({ message: 'Error al intentar agregar la tarea' });
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
            return res.status(500).json({ message: 'Error al intentar obtener las tareas ingresadas' });
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
            return res.status(500).json({ message: 'Error al intentar filtrar una tarea en especifico' });
        }
        return res.status(200).json({ taskDetail });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

// Función para el Widget
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
            return res.status(500).json({ message: 'Error al obtener las tareas urgentes' });
        }
        return res.status(200).json({ upcomingTasks });
    } catch (err) {
        return res.status(500).json({ message: 'Error en el servidor', error: err.message });
    }
};

module.exports = { addTask, getTasksList, getTaskDetail, getUpcomingTasks };