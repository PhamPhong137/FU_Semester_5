import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const products = [
    { name: "Sản phẩm 1", category: "Điện tử", price: 1200, imageUrl: "/images/event-8.jpg" },
    { name: "Sản phẩm 2", category: "Thời trang", price: 300, year: 2021, imageUrl: "/images/event-1.jpg" },
    { name: "Sản phẩm 3", category: "Đồ gia dụng", price: 150, year: 2020, imageUrl: "/images/event-2.jpg" },
    { name: "Sản phẩm 4", category: "Thực phẩm", price: 50, year: 2022, imageUrl: "/images/event-3.jpg" },
    { name: "Sản phẩm 5", category: "Điện tử", price: 800, year: 2018, imageUrl: "/images/event-4.jpg" },
    { name: "Sản phẩm 6", category: "Thể thao", price: 250, year: 2020, imageUrl: "/images/event-5.jpg" },
    { name: "Sản phẩm 7", category: "Giáo dục", price: 100, year: 2019, imageUrl: "/images/event-6.jpg" },
    { name: "Sản phẩm 8", category: "Văn phòng phẩm", price: 20, year: 2021, imageUrl: "/images/event-7.jpg" },
    { name: "Sản phẩm 9", category: "Thời trang", price: 500, year: 2019, imageUrl: "/images/event-8.jpg" },
    { name: "Sản phẩm 10", category: "Sức khỏe", price: 600, year: 2022, imageUrl: "/images/event-1.jpg" },
    { name: "Sản phẩm 11", category: "Du lịch", price: 700, year: 2018, imageUrl: "/images/event-2.jpg" },
    { name: "Sản phẩm 12", category: "Công nghệ", price: 1100, year: 2020, imageUrl: "/images/event-3.jpg" },
    { name: "Sản phẩm 13", category: "Thời trang", price: 400, year: 2022, imageUrl: "/images/event-4.jpg" },
    { name: "Sản phẩm 14", category: "Điện tử", price: 850, year: 2021, imageUrl: "/images/event-5.jpg" },
    { name: "Sản phẩm 15", category: "Nội thất", price: 250, year: 2019, imageUrl: "/images/event-6.jpg" },
    { name: "Sản phẩm 16", category: "Thể thao", price: 550, year: 2018, imageUrl: "/images/event-7.jpg" },
    { name: "Sản phẩm 17", category: "Đồ chơi", price: 60, year: 2020, imageUrl: "/images/event-8.jpg" },
    { name: "Sản phẩm 18", category: "Sức khỏe", price: 320, year: 2021, imageUrl: "/images/event-1.jpg" },
    { name: "Sản phẩm 19", category: "Giáo dục", price: 180, year: 2019, imageUrl: "/images/event-2.jpg" },
    { name: "Sản phẩm 20", category: "Văn phòng phẩm", price: 90, year: 2022, imageUrl: "/images/event-3.jpg" },
  ];


function AddNewProduct() {
    const [product, setProducts] = useState(products);
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [category, setCategory] = useState('');
    const [year, setYear] = useState('');
  
    const addProduct = (e) => {
      e.preventDefault();
      const newProduct = { name, price, category, year };
      setProducts([...product, newProduct]);
      setName('');
      setPrice('');
      setCategory('');
      setYear('');
    };
  
    return (
      <Container>
        <Row className="mb-3">
          <Col md={6}>
            <h2>Add New Product</h2>
            <Form onSubmit={addProduct}>
              <Form.Group className="mb-3">
                <Form.Label>Name</Form.Label>
                <Form.Control type="text" placeholder="Enter product name" value={name} onChange={(e) => setName(e.target.value)} />
              </Form.Group>
  
              <Form.Group className="mb-3">
                <Form.Label>Price</Form.Label>
                <Form.Control type="number" placeholder="Enter price" value={price} onChange={(e) => setPrice(e.target.value)} />
              </Form.Group>
  
              <Form.Group className="mb-3">
                <Form.Label>Category</Form.Label>
                <Form.Control type="text" placeholder="Enter category" value={category} onChange={(e) => setCategory(e.target.value)} />
              </Form.Group>
  
              <Form.Group className="mb-3">
                <Form.Label>Year of Production</Form.Label>
                <Form.Control type="number" placeholder="Enter year" value={year} onChange={(e) => setYear(e.target.value)} />
              </Form.Group>
  
              <Button variant="primary" type="submit">
                Add Product
              </Button>
            </Form>
          </Col>
        </Row>
        
        <Row>
          <Col>
            <h2>Product List</h2>
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Price</th>
                  <th>Category</th>
                  <th>Year</th>
                </tr>
              </thead>
              <tbody>
                {product.map((product, index) => (
                  <tr key={index}>
                    <td>{product.name}</td>
                    <td>${product.price}</td>
                    <td>{product.category}</td>
                    <td>{product.year}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </Col>
        </Row>
      </Container>
    );
}

export default AddNewProduct
