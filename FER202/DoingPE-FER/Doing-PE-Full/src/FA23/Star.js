import React from "react";
import { Container, Row, Col, Form, Button, Alert } from "react-bootstrap";
import { useState } from "react";
import axios from "axios";
function Star() {
  const [fullName, setFullName] = useState("");
  const [dob, setDob] = useState("");
  const [gender, setGender] = useState(true);
  const [nationality, setNationality] = useState("");
  const [description, setDecription] = useState("");

  const [message, setMessage] = useState("");
  const [success, setSuccess] = useState("");
  const hangleSubmit = async (e) => {
    e.preventDefault();
    if (fullName == "") {
      setMessage("Fullname is required");
      return;
    }
    setMessage("");
    const newStar = {
      FullName: fullName,
      Male: gender,
      Dob: dob,
      Description: description,
      Nationality: nationality,
    };

    console.log(newStar);
    await axios.post("http://localhost:9999/stars", newStar);
    setSuccess("Created successfully.");
  };

  const resetForm = (e) => {
    e.preventDefault();
    setFullName("");
    setDob("");
    setGender(null);
    setNationality("");
    setDecription("");
  };
  console.log(fullName);
  return (
    <Container>
      <h1>Create a new Star</h1>
      {success.length > 0 && (
        <Alert key="success" variant="success">
          {success}
        </Alert>
      )}

      <Row>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Id</Form.Label>
            <Form.Control type="text" disabled placeholder="0" />
          </Form.Group>
        </Col>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Fullname</Form.Label>
            <Form.Control
              type="text"
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
            />
          </Form.Group>
          {message.length > 0 && <p style={{ color: "red" }}>{message}</p>}
        </Col>
      </Row>
      <Row>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Date of birth</Form.Label>
            <Form.Control
              type="date"
              value={dob}
              onChange={(e) => setDob(e.target.value)}
            />
          </Form.Group>
        </Col>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Gender</Form.Label>
            <div style={{ display: "flex" }}>
              <Form.Check
                name="gender"
                type="radio"
                aria-label="radio 1"
                label="Male"
                checked={gender === true}
                onChange={(e) => setGender(true)}
              />
              <Form.Check
                name="gender"
                type="radio"
                aria-label="radio 1"
                label="Female"
                checked={gender === false}
                onChange={(e) => setGender(false)}
              />
            </div>
          </Form.Group>
        </Col>
      </Row>
      <Row>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Nationality </Form.Label>
            <Form.Select
              aria-label="Default select example"
              value={nationality}
              onChange={(e) => setNationality(e.target.value)}
            >
              <option>--Select--</option>
              <option value="USA">USA</option>
              <option value="England">England</option>
              <option value="France">France</option>
            </Form.Select>
          </Form.Group>
        </Col>
        <Col>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Description</Form.Label>
            <Form.Control
              type="text"
              value={description}
              onChange={(e) => setDecription(e.target.value)}
            />
          </Form.Group>
        </Col>
      </Row>
      <div style={{ display: "flex", justifyContent: "center", gap: "10px" }}>
        <Button
          type="button"
          onClick={(e) => hangleSubmit(e)}
          variant="primary"
        >
          Add
        </Button>{" "}
        <Button onClick={(e) => resetForm(e)} variant="danger">
          Reset
        </Button>{" "}
      </div>
    </Container>
  );
}

export default Star;
