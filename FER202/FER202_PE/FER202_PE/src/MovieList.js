import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Container, ListGroup, InputGroup, FormControl, Row, Col } from 'react-bootstrap';

const MovieList = () => {
  const [allMovies, setAllMovies] = useState([]);
  const [filteredMovies, setFilteredMovies] = useState([]);
  const [producers, setProducers] = useState([]);
  const [selectedProducerId, setSelectedProducerId] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      const responses = await Promise.all([
        axios.get('http://localhost:9999/movies'),
        axios.get('http://localhost:9999/directors'),
        axios.get('http://localhost:9999/stars'),
        axios.get('http://localhost:9999/movie_star'),
        axios.get('http://localhost:9999/producers')
      ]);

      const aggregatedData = aggregateMovieData(
        responses[0].data,
        responses[1].data,
        responses[2].data,
        responses[3].data
      );

      setAllMovies(aggregatedData);
      setProducers(responses[4].data);
    };

    fetchData();
  }, []);

  useEffect(() => {
    setFilteredMovies(allMovies.filter(movie => {
      return (!selectedProducerId || movie.ProducerId === selectedProducerId) &&
        movie.Title.toLowerCase().includes(searchTerm.toLowerCase());
    }));
  }, [selectedProducerId, searchTerm, allMovies]);

  const handleProducerClick = (producerId) => {
    setSelectedProducerId(producerId);
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const aggregateMovieData = (movies, directors, stars, movieStars) => {
    const directorsMap = directors.reduce((acc, director) => {
      acc[director.id] = director.FullName;
      return acc;
    }, {});
  
    const starsMap = stars.reduce((acc, star) => {
      acc[star.id] = star.FullName;
      return acc;
    }, {});
  
    const movieStarsMap = movieStars.reduce((acc, movieStar) => {
      if (!acc[movieStar.MovieId]) {
        acc[movieStar.MovieId] = [];
      }
      const starName = starsMap[movieStar.StarId];
      if (starName) {
        acc[movieStar.MovieId].push(starName);
      }
      return acc;
    }, {});
  
    return movies.map(movie => ({
      ...movie,
      DirectorName: directorsMap[movie.DirectorId] || 'Unknown',
      StarNames: movieStarsMap[movie.id] ? movieStarsMap[movie.id].join(', ') : 'No Stars'
    }));
  };

  return (
    <Container fluid>
      <Row>
        <Col md={3}>
          <h3>Producers</h3>
          <ListGroup>
            {producers.map(producer => (
              <ListGroup.Item
                key={producer.id}
                action
                onClick={() => handleProducerClick(producer.id)}
                active={producer.id === selectedProducerId}
              >
                {producer.Name}
              </ListGroup.Item>
            ))}
            <ListGroup.Item action onClick={() => handleProducerClick(null)}>
              Show All
            </ListGroup.Item>
          </ListGroup>
        </Col>
        <Col md={9}>
          <InputGroup className="mb-3">
            <FormControl
              placeholder="Enter movie title to search..."
              aria-label="Search Movies"
              onChange={handleSearchChange}
              value={searchTerm}
            />
          </InputGroup>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Release Date</th>
                <th>Description</th>
                <th>Language</th>
                <th>Director</th>
                <th>Stars</th>
              </tr>
            </thead>
            <tbody>
              {filteredMovies.map(movie => (
                <tr key={movie.id}>
                  <td>{movie.id}</td>
                  <td>{movie.Title}</td>
                  <td>{movie.ReleaseDate}</td>
                  <td>{movie.Description}</td>
                  <td>{movie.Language}</td>
                  <td>{movie.DirectorName}</td>
                  <td>{movie.StarNames}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
};

export default MovieList;


// Aggregate data from various arrays into a single array of movie information

