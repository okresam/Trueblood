import React from 'react';
import DonorsList from "./CommonComponents/DonorsList";
import BloodChange from "./EmployeeComponents/BloodChange";
import EmployeeData from "./EmployeeComponents/EmployeeData";
import 'bootstrap/dist/css/bootstrap.min.css';
import './Pills.css';
import { Tab, Row, Col, Nav } from 'react-bootstrap';

function Employee(props) {

    return(
        <div>
            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                <Row className="flex-column flex-md-row">
                    <Col className="col-md-2 mb-1">
                        <Nav variant="pills" className="flex-column nav-justified">
                            <Nav.Item>
                                <Nav.Link eventKey="first">Moji podaci</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="second">Popis donora</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="third">Evidencija krvi</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col className="col-md-10">
                        <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <EmployeeData mode="EMPLOYEE_ACCESSING_DATA"/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="second">
                                    <DonorsList/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="third">
                                    <BloodChange/>
                                </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </div>
    )
}

export default Employee;