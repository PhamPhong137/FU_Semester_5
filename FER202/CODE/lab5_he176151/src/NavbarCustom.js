import React from "react";
import { useNavigate } from "react-router-dom";
import { Navbar, Nav, Button, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

function NavbarCustom() {
  const navigate = useNavigate();
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  const handleLogout = () => {
    localStorage.setItem("isLoggedIn", "false");
    navigate("/");
  };

  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand >
          Quản Lý Đơn Hàng
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/orders">
              Đơn hàng
            </Nav.Link>
            <Nav.Link as={Link} to="/add-order">
              Thêm Đơn hàng
            </Nav.Link>
            {!isLoggedIn && (
              <Button
                as={Link}
                to="/login"
                variant="outline-primary"
                onClick={handleLogout}
              >
                Đăng nhập
              </Button>
            )}
            {isLoggedIn && (
              <Button variant="outline-primary" onClick={handleLogout}>
                Đăng xuất
              </Button>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavbarCustom;
