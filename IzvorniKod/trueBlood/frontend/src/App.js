import React from 'react'
import Header from './components/Header'
import LoginForm from './components/LoginForm'
import SignInForm from './components/SignInForm'
import Home from './components/Home'
import {BrowserRouter, Switch, Route} from "react-router-dom";
import AuthHandler from "./components/AuthHandler";
import Admin from './components/Admin';
import Employee from './components/Employee';
import Donor from './components/Donor';
import Confirm from './components/Confirm';
import './components/Test.css';


function App() {

  const [log, setLog] = React.useState("");

  if(AuthHandler.isUserLoggedIn()) {
    AuthHandler.setupAxiosInterceptors(AuthHandler.createBasicAuthToken(AuthHandler.getLoggedInUserName(), AuthHandler.getLoggedInPassword()));
  }

  function logSet(isLoggedIn){
    setLog(isLoggedIn);
  }

  return (
    <BrowserRouter>
      <Header log={log} logSet={logSet}/>
        <div className="App">
            <Switch>
              <Route exact path='/login'>
                <LoginForm logSet={logSet}/>
              </Route>
              <Route exact path='/signin'>
                <SignInForm/>
              </Route>
              <Route exact path='/'>
                <Home/>
              </Route>
              <Route exact path='/home'>
                <Home/>
              </Route>
              <Route exact path='/admin'>
                <Admin/>
              </Route>
              <Route exact path='/employee'>
                <Employee/>
              </Route>
              <Route exact path='/donor'>
                <Donor/>
              </Route>
              <Route exact path='/user/add/confirm'>
                <Confirm/>
              </Route>
              <Route exact path='/admin'>
                <Admin/>
              </Route>
              <Route exact path='/employee'>
                <Employee/>
              </Route>
              <Route exact path='/donor'>
                <Donor/>
              </Route>
            </Switch>
        </div>
    </BrowserRouter>
  );
}

export default App;
