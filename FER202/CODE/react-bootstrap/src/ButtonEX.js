import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
 
function ButtonEX() {
  return (
    <div>
      <Button variant="primary">Primary</Button>{" "}
      <Button variant="secondary">Secondary</Button>{" "}

      <Container>
        <Row>
          <Col>1 of 2</Col>
          <Col>1 of 2</Col>
       
          <Col>1 of 2</Col>
          <Col>2 of 2</Col>
        </Row>
      </Container>

    </div>
  );
}

export default ButtonEX;
