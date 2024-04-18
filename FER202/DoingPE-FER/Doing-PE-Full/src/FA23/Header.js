import React from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

function Header() {
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>Dashboard</h1>
      <div style={{textAlign:"center",display:"flex",justifyContent:"center",gap:"15px"}}>
        <Button as={Link} to="/director" variant="success">Directors</Button>{" "}
        <Button as={Link} to="/producer" variant="info">Producer</Button>{" "}
        <Button as={Link} to="/star" variant="danger">Star</Button>{" "}
        <Button variant="secondary">Genres</Button>{" "}
        <Button as={Link} to="/movie" variant="warning">Movies</Button>{" "}
      
      </div>
    </div>
  );
}

export default Header;
