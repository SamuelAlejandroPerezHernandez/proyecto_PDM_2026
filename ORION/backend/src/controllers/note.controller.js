const supabase = require('../config/supabase')

const addNote = async(req, res) => {
    const {title, content} = req.body

    const userId = req.user.id
    const categoryId = req.body.category_id

    if(!title){
        return res.status(400).json({message: 'El titulo es requerido'})
    }

    const {data: newNote, error} = await supabase
        .from('task')
        .insert({
            user_id: userId,
            category_id: categoryId,
            title,
            content
        })
        .select()
        .single()

        if(error){
            return res.status(500).json({message: 'Error al intentar agregar la nota'})
        }

    res.status(201).json({
        newNote
    })
}

const getNotesList = async(req, res) => {
    const {data: notesList, error} = await supabase
        .from('notes')
        .select('*')
        .eq('user_id', req.user.id)

    if(error){
        return res.status(500).json({message: 'Error al intentar obtener las notas ingresadas'})
    }

    res.status(200).json({
        notesList
    })
}

const getNoteDetail = async(req, res) => {
    const {id} = req.params

    const {data: noteDetail, error} = await supabase
        .from('notes')
        .select('*')
        .eq('id', id)
        .single()

    if(error){
        return res.status(500).json({message: 'Error al intentar filtrar una nota en especifico'})
    }

    res.status(200).json({
        noteDetail
    })
}