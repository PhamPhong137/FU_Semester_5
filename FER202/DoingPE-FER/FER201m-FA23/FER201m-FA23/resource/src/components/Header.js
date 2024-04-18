import { Row, Button } from 'react-bootstrap'
import { Link } from 'react-router-dom';

export default function Header() {
    return (
        <div >
            <h3 style={{ textAlign: "center", padding: '12px 12px 8px'}}>Dashboard</h3>
            <Row className="justify-content-center mt-4 mb-4" >
              <Button style={{marginRight:'12px'}} variant="success" ><Link to={'/director'}  style={{textDecoration: 'none', color: 'white' }}>Directors</Link></Button>
              <Button style={{marginRight:'12px', backgroundColor: 'rgb(28, 175, 197)'}} ><Link to={'/producer'} style={{textDecoration: 'none', color: 'white'}} >Producers</Link></Button>
              <Button style={{marginRight:'12px'}} variant="danger"><Link to={'/star'} style={{textDecoration: 'none', color: 'white'}} >Stars</Link></Button>
              <Button style={{marginRight:'12px', backgroundColor: 'gray'}} variant="danger"><Link to={'/genre'} style={{textDecoration: 'none', color: 'white'}} >Genres</Link></Button>
              <Button style={{marginRight:'12px', backgroundColor: 'rgb(242, 175, 18)'}} ><Link to={'/movie'} style={{textDecoration: 'none', color: 'black'}} >Movies</Link></Button>
          </Row>
        </div>
    )
}
