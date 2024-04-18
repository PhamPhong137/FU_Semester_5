const Header = () => {
    return (
        <div className="col-12">
            <h1 className="col-12 text-center py-2">Dashboard</h1>
            <div className="col-12 d-flex justify-content-center">
                <button className="mx-2 btn btn-success">
                    <a href='http://localhost:3000/director' style={{ textDecoration: 'none', color: 'white' }}>
                        Directors
                    </a>
                </button>
                <button className="mx-2 btn btn-primary">
                    <a href='http://localhost:3000/producer' style={{ textDecoration: 'none', color: 'white' }}>
                        Producers
                    </a>
                </button>
                <button className="mx-2 btn btn-danger">
                    <a href='http://localhost:3000/star' style={{ textDecoration: 'none', color: 'white' }}>
                        Stars
                    </a>
                </button>
                <button className="mx-2 btn btn-secondary">
                    <a href='http://localhost:3000/genre' style={{ textDecoration: 'none', color: 'white' }}>
                        Genres
                    </a>
                </button>
                <button className="mx-2 btn btn-warning">
                    <a href='http://localhost:3000/movie' style={{ textDecoration: 'none', color: 'white' }}>
                        Movies
                    </a>
                </button>
            </div>
        </div>
    )
}

export default Header