import { Link } from 'react-router-dom';
import { Col, Container, Row, Table, Form, Button, Alert } from 'react-bootstrap';
import { useEffect, useState } from "react";
import axios from "axios";
export default function Star() {
    const [fullName, setFullName] = useState('');
    const [dob, setDob] = useState('');
    const [gender, setGender] = useState(true);
    const [nationality, setNationality] = useState('');
    const [description, setDecription] = useState('');

    const [message, setMessage] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        if (fullName=== '' ) {
            setMessage('Fullname is required'); 
            return; 
        } 
        setMessage('');

        axios.post("http://localhost:9999/stars", {
            FullName: fullName,
            Male: gender,
            Dob: dob,
            Description: description,
            Nationality: nationality
        }).then(res => {
            if (res.status === 201)
                setSuccess("Created successfully")
        }).catch(err => console.error(err))
    }

    return (
        <Container>
            <Row>
                <Col>
                    <h2 className="pt-2" >Create a new star</h2>
                    {success.length > 0 && <Alert variant="success">{success}</Alert>}
                </Col>
            </Row>
            <Form onSubmit={e => handleSubmit(e)}>
                <Row className="justify-content-center pt-2 mt-2 mb-3">
                    <Col md={6} >
                        <Form.Group controlId="productId">
                            <Form.Label>ID</Form.Label>
                            <Form.Control type="number" value="0" placeholder="Id" disabled />
                        </Form.Group>
                    </Col>
                    <Col md={6} >
                        <Form.Group controlId="productName">
                            <Form.Label>
                                Fullname <span className="text-danger">*</span>
                            </Form.Label>
                            <Form.Control type="text" onChange={e => setFullName(e.target.value)} />
                            {message.length > 0 && <p style={{ color: 'red' }}>{message}</p>}
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center mb-3">
                    <Col md={6} >
                        <Form.Group controlId="productPrice">
                            <Form.Label>Date of birth</Form.Label>
                            <Form.Control type="date" onChange={e => setDob((e.target.value))} />
                        </Form.Group>
                    </Col>
                    <Col md={6} >
                        <Form.Group controlId="productQuantity">
                            <Form.Label>Gender</Form.Label>
                            <Row>
                                <Form.Check
                                type="radio"
                                label="Male"
                                name="gender"
                                style={{marginLeft:'20px'}}
                                checked={gender}
                                onChange={() => setGender(true)}
                            />
                            <Form.Check
                                type="radio"
                                label="Female"
                                name="gender"
                                style={{marginLeft:'12px'}}
                                checked={!gender}
                                onChange={() => setGender(false)}
                            /></Row>
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center mb-3">
                    <Col md={6} >
                        <Form.Group controlId="productCategory">
                            <Form.Label>Nationality</Form.Label>
                            <Form.Control as="select" onChange={e => setNationality((e.target.value))}>
                                <option value="">--Select--</option>
                                <option value="USA">USA</option>
                                <option value="England">England</option>
                                <option value="France">France</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={6} >
                        <Form.Group controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Description</Form.Label>
                            <Form.Control onChange={e => setDecription(e.target.value)} />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center mt-4 mb-4">
                    <Button style={{ width: '120px', marginRight: '12px' }} variant="primary" type="submit">
                        Add
                    </Button>
                    <Button variant="danger"><Link to={'/'} style={{ textDecoration: 'none', color: 'white' }} > Reset</Link></Button>
                </Row>
            </Form>
        </Container>
    );
}
