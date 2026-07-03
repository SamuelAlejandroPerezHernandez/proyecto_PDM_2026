const supabase = require('../config/supabase')

const addNote = async (req, res) => {
    const { title, content } = req.body
    const userId = req.user.id
    const categoryId = req.body.category_id

    if (!title) {
        return res.status(400).json({ message: 'El titulo es requerido' })
    }

    const { data: newNote, error } = await supabase
        .from('notes')
        .insert({
            user_id: userId,
            category_id: categoryId,
            title,
            content
        })
        .select()
        .single()

    if (error) {
        console.log('🚨 ERROR SUPABASE NOTES:', JSON.stringify(error, null, 2))
        return res.status(500).json({
            message: 'Error al intentar agregar la nota',
            detalle: error.message,
            code: error.code,
            hint: error.hint
        })
    }

    res.status(201).json({ newNote })
}

const getNotesList = async (req, res) => {
    const { data: notesList, error } = await supabase
        .from('notes')
        .select('*')
        .eq('user_id', req.user.id)

    if (error) {
        return res.status(500).json({ message: 'Error al intentar obtener las notas ingresadas' })
    }
    res.status(200).json({ notesList })
}

const getNoteDetail = async (req, res) => {
    const { id } = req.params
    const { data: noteDetail, error } = await supabase
        .from('notes')
        .select('*')
        .eq('id', id)
        .single()

    if (error) {
        return res.status(500).json({ message: 'Error al intentar filtrar una nota en especifico' })
    }
    res.status(200).json({ noteDetail })
}

const updateNote = async (req, res) => {
    const { id } = req.params
    const { id: noteId, user_id, ...camposAActualizar } = req.body

    const { data: UpdateNote, error } = await supabase
        .from('notes')
        .update(camposAActualizar)
        .eq('id', id)
        .select()
        .single()

    if (error) {
        console.log('🚨 ERROR SUPABASE UPDATE_NOTE:', JSON.stringify(error, null, 2))
        return res.status(500).json({
            message: 'Error al intentar actualizar la nota',
            detalle: error.message
        })
    }

    res.status(200).json({ UpdateNote })
}

const deleteNote = async (req, res) => {
    const { id } = req.params

    const { data: deletedNote, error } = await supabase
        .from('notes')
        .delete()
        .eq('id', id)
        .select()
        .single()

    if (error) {
        console.log('🚨 ERROR SUPABASE DELETE_NOTE:', JSON.stringify(error, null, 2))
        return res.status(500).json({
            message: 'Error al intentar eliminar la nota',
            detalle: error.message
        })
    }

    res.status(200).json({ deleteNote: deletedNote })
}

module.exports = { addNote, getNotesList, getNoteDetail, updateNote, deleteNote }