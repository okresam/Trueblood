import React from 'react';
import EmployeesList from "./AdminComponents/EmployeesList";
import Limits from "./AdminComponents/Limits";
import DonorsList from "./CommonComponents/DonorsList";
import { Tab, Row, Col, Nav } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function Admin(props) {
   
    return(
        <div>
            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                <Row className="flex-column flex-md-row">
                    <Col className="col-md-2 mb-1">
                        <Nav variant="pills" className="flex-column nav-justified">
                            <Nav.Item>
                                <Nav.Link eventKey="first">Popis djelatnika</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="second">Popis donora</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="third">Definiraj granice</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col className="col-12 col-md-10">
                        <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <EmployeesList/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="second">
                                    <DonorsList mode="ADMIN"/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="third">
                                    <Limits/>
                                </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>

        </div>

       

    )
}

export default Admin;