import axios from "axios";
import React, { useEffect, useState } from "react";
import { Form, InputGroup, Row, Col, Table } from "react-bootstrap";
import { Link } from "react-router-dom";

function Movie() {
  const [movies, setMovie] = useState([]);
  const [filterMovies, setFilterMovies] = useState([]);
  const [directors, setDirector] = useState([]);
  const [producers, setProducer] = useState([]);
  const [moviestars, setMoviestar] = useState([]);
  const [stars, setStar] = useState([]);
  const [querySearch, setQuerySearch] = useState("");
  const [producerStore, setProducerStore] = useState([]);
  useEffect(() => {
    fetchMovies();
    fetchDirectors();
    fetchProducers();
    fetchMovieStar();
    fetchStar();
  }, []);

  const fetchMovies = async () => {
    const response = await axios.get("http://localhost:9999/movies");
    setMovie(response.data);
    setFilterMovies(response.data);
    setProducerStore(response.data);
  };

  const fetchDirectors = async () => {
    const response = await axios.get("http://localhost:9999/directors");
    setDirector(response.data);
  };
  const fetchProducers = async () => {
    const response = await axios.get("http://localhost:9999/producers");
    setProducer(response.data);
    
  };
  const fetchMovieStar = async () => {
    const response = await axios.get("http://localhost:9999/movie_star");
    setMoviestar(response.data);
  };
  const fetchStar = async () => {
    const response = await axios.get("http://localhost:9999/stars");
    setStar(response.data);
  };

  const getDirecterNameById = (id) => {
    return directors?.find((d) => d.id === id)?.FullName;
  };

  const getStarNameById = (id) => {
    const starids = moviestars
      .filter((moviestar) => moviestar.MovieId === id)
      ?.map((movie) => movie.StarId);
    return stars
      .filter((s) => starids.includes(s.id))
      .map((star) => star.FullName)
      .join(", ");
  };
  const setFilterdMovies = (id) => {
    if (id != null) {
      setFilterMovies(movies?.filter((m) => m.ProducerId === id));
      setProducerStore(movies?.filter((m) => m.ProducerId === id));
    } else {
      setFilterMovies(movies);
    }
  };
  useEffect(() => {
    if (querySearch.trim() === "") {
      setFilterMovies(producerStore);
    } else {
      setFilterMovies(
        filterMovies.filter((m) =>
          m.Title.toLowerCase().includes(querySearch.toLowerCase())
        )
      );
    }
  }, [querySearch, movies]);

  return (
    <div>
      <h1 style={{ textAlign: "center" }}>Movies Management</h1>
      <InputGroup className="mb-3" style={{ margin: "auto", width: "400px" }}>
        <Form.Control
          placeholder="Search"
          aria-label="Username"
          aria-describedby="basic-addon1"
          onChange={(e) => setQuerySearch(e.target.value)}
        />
      </InputGroup>
      <Row>
        <Col sm="2">
          <h1>Producers</h1>
          {producers.map((producer) => (
            <Link
              to={`/movie/?producer-id=${producer.id}`}
              onClick={() => setFilterdMovies(producer.id)}
            >
              <li>{producer.Name}</li>
            </Link>
          ))}
          <li>
            <Link
              to={`/movie/?producer-id`}
              onClick={() => setFilterdMovies(null)}
            >
              Show All
            </Link>
          </li>
        </Col>
        <Col sm="9">
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Id</th>
                <th>Title</th>
                <th>RealaseDate</th>
                <th>Description</th>
                <th>Langage</th>
                <th>Director</th>
                <th>Star</th>
              </tr>
            </thead>
            <tbody>
              {filterMovies.map((movie) => (
                <tr>
                  <td>{movie.id}</td>
                  <td>{movie.Title}</td>
                  <td>{movie.ReleaseDate}</td>
                  <td>{movie.Description}</td>
                  <td>{movie.Language}</td>
                  <td>{getDirecterNameById(movie.DirectorId)}</td>
                  <td>{getStarNameById(movie.id)}</td>
                </tr>
              ))}
              <tr></tr>
            </tbody>
          </Table>
        </Col>
      </Row>
    </div>
  );
}

export default Movie;
