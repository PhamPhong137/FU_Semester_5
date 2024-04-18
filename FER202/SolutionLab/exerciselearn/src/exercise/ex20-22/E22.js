import React from 'react';
import { BrowserRouter as Router, Link, Routes, Route } from 'react-router-dom';
import { Navbar, Nav } from 'react-bootstrap';


const Home = () => <div align='center'>Home Component</div>;
const About = () => <div align='center'>About Component</div>;
const Contact = () => <div align='center'>Contact Component</div>;
const Profile = () => <div align='center'>Profile Component</div>;

const CustomNavbar = () => {
    return (
      <Navbar bg="dark" variant="dark" expand="md">
        <Navbar.Brand as={Link} to="/">Logo</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link as={Link} to="/" exact>Home</Nav.Link>
            <Nav.Link as={Link} to="/about">About</Nav.Link>
            <Nav.Link as={Link} to="/contact">Contact</Nav.Link>
            <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  };

const E22 = () => {
  return (
    <Router>
      <CustomNavbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>     
    </Router>
  );
};

export default E22;
