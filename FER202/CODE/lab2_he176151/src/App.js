import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";
import { Table, Row, Col, Container } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Carousel from "react-bootstrap/Carousel";
import Card from "react-bootstrap/Card";
import Modal from "react-bootstrap/Modal";
import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { useState } from "react";
import { FormControl } from "react-bootstrap";
import AddNewProduct from "./AddNewProduct";

const products = [
  {
    name: "Sản phẩm 1",
    category: "Điện tử",
    price: 1200,
    imageUrl: "/images/event-8.jpg",
  },
  {
    name: "Sản phẩm 2",
    category: "Thời trang",
    price: 300,
    year: 2021,
    imageUrl: "/images/event-1.jpg",
  },
  {
    name: "Sản phẩm 3",
    category: "Đồ gia dụng",
    price: 150,
    year: 2020,
    imageUrl: "/images/event-2.jpg",
  },
  {
    name: "Sản phẩm 4",
    category: "Thực phẩm",
    price: 50,
    year: 2022,
    imageUrl: "/images/event-3.jpg",
  },
  {
    name: "Sản phẩm 5",
    category: "Điện tử",
    price: 800,
    year: 2018,
    imageUrl: "/images/event-4.jpg",
  },
  {
    name: "Sản phẩm 6",
    category: "Thể thao",
    price: 250,
    year: 2020,
    imageUrl: "/images/event-5.jpg",
  },
  {
    name: "Sản phẩm 7",
    category: "Giáo dục",
    price: 100,
    year: 2019,
    imageUrl: "/images/event-6.jpg",
  },
  {
    name: "Sản phẩm 8",
    category: "Văn phòng phẩm",
    price: 20,
    year: 2021,
    imageUrl: "/images/event-7.jpg",
  },
  {
    name: "Sản phẩm 9",
    category: "Thời trang",
    price: 500,
    year: 2019,
    imageUrl: "/images/event-8.jpg",
  },
  {
    name: "Sản phẩm 10",
    category: "Sức khỏe",
    price: 600,
    year: 2022,
    imageUrl: "/images/event-1.jpg",
  },
  {
    name: "Sản phẩm 11",
    category: "Du lịch",
    price: 700,
    year: 2018,
    imageUrl: "/images/event-2.jpg",
  },
  {
    name: "Sản phẩm 12",
    category: "Công nghệ",
    price: 1100,
    year: 2020,
    imageUrl: "/images/event-3.jpg",
  },
  {
    name: "Sản phẩm 13",
    category: "Thời trang",
    price: 400,
    year: 2022,
    imageUrl: "/images/event-4.jpg",
  },
  {
    name: "Sản phẩm 14",
    category: "Điện tử",
    price: 850,
    year: 2021,
    imageUrl: "/images/event-5.jpg",
  },
  {
    name: "Sản phẩm 15",
    category: "Nội thất",
    price: 250,
    year: 2019,
    imageUrl: "/images/event-6.jpg",
  },
  {
    name: "Sản phẩm 16",
    category: "Thể thao",
    price: 550,
    year: 2018,
    imageUrl: "/images/event-7.jpg",
  },
  {
    name: "Sản phẩm 17",
    category: "Đồ chơi",
    price: 60,
    year: 2020,
    imageUrl: "/images/event-8.jpg",
  },
  {
    name: "Sản phẩm 18",
    category: "Sức khỏe",
    price: 320,
    year: 2021,
    imageUrl: "/images/event-1.jpg",
  },
  {
    name: "Sản phẩm 19",
    category: "Giáo dục",
    price: 180,
    year: 2019,
    imageUrl: "/images/event-2.jpg",
  },
  {
    name: "Sản phẩm 20",
    category: "Văn phòng phẩm",
    price: 90,
    year: 2022,
    imageUrl: "/images/event-3.jpg",
  },
];
function c3(products) {
  const category = products.map((product) => product.category);
  const uniqueCategory = [...new Set(category)];
  return (
    <div>
      <NavDropdown title="Product category">
        {uniqueCategory.map((category) => (
          <NavDropdown.Item>{category}</NavDropdown.Item>
        ))}
      </NavDropdown>
    </div>
  );
}
function c4(products) {
  const category = products.map((product) => product.category);
  const uniqueCategory = [...new Set(category)];
  return (
    <div>
      <NavDropdown title="Product category">
        {uniqueCategory.map((category) => (
          <NavDropdown.Item>{category}</NavDropdown.Item>
        ))}
      </NavDropdown>
      <Carousel>
        {products.map((product) => (
          <Carousel.Item interval={1000} style={{ textAlign: "center" }}>
            Price: {product.price}
          </Carousel.Item>
        ))}
      </Carousel>
    </div>
  );
}
function c5(products) {
  return (
    <div>
      <Carousel>
        {products.map((product) => (
          <Carousel.Item interval={1000} style={{ textAlign: "center" }}>
            <img width={900} height={500} src={product.imageUrl} />
          </Carousel.Item>
        ))}
      </Carousel>
    </div>
  );
}
function c6(products) {
  return (
    <div>
      {products.map((product) => (
        <Card style={{ width: "18rem" }}>
          <Card.Img variant="top" src={product.imageUrl} />
          <Card.Body>
            <Card.Title>{product.name}</Card.Title>
            <Card.Text>{product.price}</Card.Text>
            <Button variant="primary">Show Details</Button>
          </Card.Body>
        </Card>
      ))}
    </div>
  );
}
function C7(products) {
  const [show, setShow] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);

  const handleClose = () => {
    setShow(false);
    setCurrentProduct(null);
  };

  const handleShow = (product) => {
    setCurrentProduct(product);
    setShow(true);
  };

  return (
    <>
      {products.map((product) => (
        <div key={product.id}>
          <Button variant="primary" onClick={() => handleShow(product)}>
            ProductName: {product.name}
          </Button>
        </div>
      ))}

      {currentProduct && (
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>category: {currentProduct.category}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            price: {currentProduct.price} year: {currentProduct.year}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      )}
    </>
  );
}

function c10(products) {
  return (
    <Container>
      <Row>
        {products.map((product) => (
          <Col md={3}>
            <Card style={{ width: "18rem" }}>
              <Card.Img variant="top" src={product.imageUrl} />
              <Card.Body>
                <Card.Title>{product.name}</Card.Title>
                <Card.Text>{product.price}</Card.Text>
                <Button variant="primary">Detail</Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
}

function c8(products) {
  const uniqueYears = [
    ...new Set(products.map((product) => product.year)),
  ].sort();

  return (
    <Tabs defaultActiveKey={uniqueYears[0]} id="product-tabs" className="mb-3">
      {uniqueYears.map((year) => (
        <Tab eventKey={year} title={year} key={year}>
          <Row md={4} className="g-4">
            {products
              .filter((product) => product.year === year)
              .map((product, idx) => (
                <Col key={idx}>
                  <Card>
                    <Card.Img
                      variant="top"
                      src={product.imageUrl}
                      width={200}
                      height={300}
                    />
                    <Card.Body>
                      <Card.Title>{product.name}</Card.Title>
                      <Card.Text>Category: {product.category}</Card.Text>
                      <Card.Text>Price: ${product.price}</Card.Text>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
          </Row>
        </Tab>
      ))}
    </Tabs>
  );
}
function C9(products) {
  const [filteredProduct, setFilteredProduct] = useState(products);

  function handleFilter(query) {
    const lowercasedQuery = query.toLowerCase();
    const filteredData = products.filter(
      (product) =>
        product.name.toLowerCase().includes(lowercasedQuery) ||
        product.category.toLowerCase().includes(lowercasedQuery) ||
        String(product.year).includes(lowercasedQuery) ||
        String(product.price).includes(lowercasedQuery)
    );
    setFilteredProduct(filteredData);
  }
  return (
    <div>
      <Form className="d-flex">
        <FormControl
          type="text"
          placeholder="Search advanced "
          className="me-2"
          aria-label="Search"
          onChange={(e) => handleFilter(e.target.value)}
        />
      </Form>

      <ul>
        {filteredProduct.map((product) => (
          <li key={product.name}>
            {product.name} - {product.category}- Price: {product.price}- Year:{" "}
            {product.year}
          </li>
        ))}
      </ul>
    </div>
  );
}

function App() {
  return (
    <div>
      <AddNewProduct />
      {c3(products)}
      {c4(products)}
      {c5(products)}
      {c6(products)}
      {C7(products)}
      {c8(products)}
      {C9(products)}
    </div>
  );
}

export default App;
