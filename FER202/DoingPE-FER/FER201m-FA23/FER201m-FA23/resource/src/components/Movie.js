import { Col, Container, Row, Form, Table } from 'react-bootstrap'
import { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import axios from "axios";

export default function Movie() {
    const [producers, setProducers] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [stars, setStars] = useState([]);
    const [movies, setMovies] = useState([]);
    const [producer, setProducer] = useState(0);
    const [searchValue, setSearchValue] = useState('');
    const [movieStars, setMovieStars] = useState([]);


    console.log(producer)

    useEffect(() => {
        axios.get("http://localhost:9999/producers")
            .then((res) => setProducers(res.data))
            .catch((err) => console.error(err));
    }, []);

    useEffect(() => {
        axios.get("http://localhost:9999/directors")
            .then((res) => setDirectors(res.data))
            .catch((err) => console.error(err));
    }, []);

    useEffect(() => {
        axios.get("http://localhost:9999/stars")
            .then((res) => setStars(res.data))
            .catch((err) => console.error(err));
    }, []);

    useEffect(() => {
        axios.get("http://localhost:9999/movies")
            .then((res) => res.data)
            .then((movies) => {
                setMovies(movies.filter((p) => p.Title.toLowerCase().startsWith(searchValue.toLowerCase()))
                    .filter((p) => producer === 0 ? true : producer === p.ProducerId))
            })
            .catch((err) => console.error(err));
    }, [producer, searchValue]);

    useEffect(() => {
        axios.get("http://localhost:9999/movie_star")
            .then((res) => setMovieStars(res.data))
            .catch((err) => console.error(err));
    }, []);
    
    return (
        <Container fluid>
            <h2 style={{ textAlign: "center", margin: '10px 0 24px' }}>Movie Management</h2>
                <div style={{ textAlign: 'center', marginBottom: '20px' }}>
                        <input
                            type="text"
                            style={{ width: '34%', lineHeight: '32px' }}
                            placeholder=' Enter movie title to search ...'
                            value={searchValue}
                            onChange={(e) => setSearchValue(e.target.value)}
                        />
                    </div>
            <Row>
                <Col md={2}>
                    <h4 style={{ marginTop: '20px' }}>Producers</h4>
                    <ul>
                        {producers.map((producer) =>(
                            <li ><Link to={`/movie/producer-id=${producer.id}`} onClick={() => setProducer(producer.id)}>{producer.Name}</Link>
                                
                            </li>
                        ))}
                    </ul>
                </Col>
                <Col md={10}>
                   
                    
                    <Table striped>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Title</th>
                                <th>ReleaseDate</th>
                                <th>Description</th>
                                <th>Language</th>
                                <th>Director</th>
                                <th>Stars</th>
                            </tr>
                        </thead>
                        <tbody>
                        {movies.map((m) => (
                            <tr key={m.id}>
                                <td>{m.id}</td>
                                <td>{m.Title}</td>
                                <td>{m.ReleaseDate}</td>
                                <td>{m.Description}</td>
                                <td>{m.Language}</td>
                                <td>{directors.find(p => p.id === m.DirectorId).FullName}</td>
                                <td>
                                    {movieStars
                                        .filter(ms => ms.MovieId === m.id)
                                        .map(ms => stars.find(s => s.id === ms.StarId).FullName)
                                        .join(', ')}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </Container>
    )
}
