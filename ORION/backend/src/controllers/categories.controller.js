const supabase = require('../config/supabase')

const getCategories = async(req, res) => {
    const {data: categories, error} = await supabase
        .from('categories')
        .select('*')
    
        if(error){
            return res.status(500).json({message: 'Error al obtener las categorias'})
        }

        res.status(200).json({
            categories
    })
}

module.exports = { getCategories }