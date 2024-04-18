import { Container, Col, Row, Form } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import AppPEtrial from "./PETrial/AppPEtrial";
import AppFA23 from "./FA23/AppFA23";

function App() {
  return (
    <div>
      {/* <AppPEtrial/> */}
      <AppFA23 />
    </div>
  );
}

export default App;
