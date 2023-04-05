import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import DonorData from "./CommonComponents/DonorData";
import DonationHistory from "./DonorComponents/DonationHistory";
import Messages from "./DonorComponents/Messages";
import { Tab, Row, Col, Nav } from 'react-bootstrap';

function Donor(props) {

    return(
        <div>
            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                <Row className="flex-column flex-md-row">
                    <Col className="col-md-2 mb-1">
                        <Nav variant="pills" className="flex-column nav-justified">
                            <Nav.Item>
                                <Nav.Link eventKey="first">Osobni podaci</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="second">Povijest donacija</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="third">Pretinac</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col className="col-12 col-md-10">
                        <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <DonorData mode="DONOR_ACCESSING_DATA" username=""/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="second">
                                    <DonationHistory/>
                                </Tab.Pane>
                                <Tab.Pane eventKey="third">
                                    <Messages/>
                                </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>

        </div>

       

    )

    /*
    <div>
                <button onClick={() => setView("myData")}>Moji podaci</button>
                <button onClick={() => setView("donationHistory")}>Povijest donacija</button>
                <button onClick={() => setView("Messages")}>Poruke</button>
                Kratki info
            </div>

            <div hidden={!(view==="DonorData")}>
                <DonorData mode="DONOR_ACCESSING_DATA" donorData={donorData} />

            </div>

            <div hidden={!(view==="donationHistory")}>
                <DonationHistory/>
            </div>

            <div hidden={!(view==="Messages")}>
                <Messages/>
            </div>

    */

}

export default Donor;