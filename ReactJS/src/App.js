import React from 'react';
//import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListEmployeeComponent from "./component/employee/ListEmployeeComponent";
import AddEmployeeComponent from "./component/employee/AddEmployeeComponent";
import EditEmployeeComponent from "./component/employee/EditEmployeeComponent";
function App() {
  return (
    <div className="container">
       <Router>
              <div className="col-md-6">
                <h1 className="text-center" style={style}>YASH</h1>
                  <Switch>
                      <Route path="/" exact component={ListEmployeeComponent} />
                      <Route path="/add-employee" component={AddEmployeeComponent} />
                      <Route path="/edit-employee" component={EditEmployeeComponent} />
                  </Switch>
              </div>
          </Router>
    </div>
  );
}
const style = {
    color: 'red',
    margin: '10px'
}
export default App;
