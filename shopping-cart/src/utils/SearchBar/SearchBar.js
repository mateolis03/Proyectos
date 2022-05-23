const SearchBarComponent = ({setProductSearch, setSelectedCategory}) => {
    const handleSetCategory = (category) => {
        setSelectedCategory(category)
    }
    const handleInputSearch = (search) => {
        setProductSearch(search)
    }

    return(
        <div className="search-container">
            <input placeholder="Buscar por producto" onChange={(e) => handleInputSearch(e.target.value)} />
            <select onChange={(e) => handleSetCategory(e.target.value)}>
                <option value="all">
                    Todos los productos
                </option>
                <option value="tech">
                    Tecnología
                </option>
                <option value="luxury">
                    Lujo
                </option>
                <option value="clothes">
                    Ropa
                </option>
            </select>
        </div>
    )
}

export default SearchBarComponent

