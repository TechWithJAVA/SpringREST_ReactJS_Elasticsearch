import React, { Component } from 'react'
import ApiService from "../../service/ApiService";
//import {Column} from 'primereact/column';
//import {DataTable} from 'primereact/datatable';
class ListEmployeeComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            employees: [ ],
            message: null 
        }
        this.reloadEmployeeList = this.reloadEmployeeList.bind(this);
        this.addEmployee = this.addEmployee.bind(this);
        this.editEmployee = this.editEmployee.bind(this);
        this.deleteEmployee = this.deleteEmployee.bind(this);
    }

    componentDidMount() {
        this.reloadEmployeeList();
    }
    reloadEmployeeList(){
        ApiService.fetchEmployees()
        .then((res) => {
            const employees = res.data;
            console.log(employees);
            this.setState({employees})
           
        });
    }
    deleteEmployee(empId) {
        ApiService.deleteEmployee(empId)
           .then(res => {
               this.setState({message : 'User deleted successfully.'});
               this.setState({employees: this.state.employees.filter(employee => employee.EmpId !== empId)});
           })

    }
    addEmployee() {
        window.localStorage.removeItem("EmpId");
        this.props.history.push('/add-employee');
    }
    editEmployee(EmpId) {
        window.localStorage.setItem("EmpId", EmpId);
        this.props.history.push('/edit-employee');
    }

    render(){
        return (

            
        //     <DataTable value={this.state.employees}>
        //     <Column field="EmpId" header="EmpId" />
        //     <Column field="EmpName" header="EmpName" />
        //     <Column field="CompanyName" header="CompanyName" />
        //     <Column field="Address.CityName" header="CityName" />
        //     <Column field="Address.State" header="State" />
        //     <Column field="Address.Country" header="Country" />
        //     <Column field="Address.Pin" header="Pin" />
        //     <Column field="Address.MobileNo" header="MobileNo" />
            
        // </DataTable>

            <div class="container">
                
                 <h2 className="text-center">Employees Details</h2>
                 <button className="btn btn-danger" style={{width:'150px'}} onClick={() => this.addEmployee()}>Add Employee</button>
                 <table className="table table-striped">
                 <thead>
                        <tr >
                        <th>EmpId</th>
                        <th>EmpName</th>
                        <th>CompanyName</th>
                        <th>CityName</th>
                        <th>State</th>
                        <th>Country</th>
                        <th>Pin</th>
                       <th>MobileNo</th>
                       <th>Action</th>
                       </tr>
                       </thead>
                       <tbody>
                           {
                                this.state.employees.map(
                                    employee=>
                                    <tr key={employee.EmpId}>
                                         <td>{employee.EmpId}</td>
                                         <td>{employee.EmpName}</td>
                                         <td>{employee.CompanyName}</td>
                                         {employee.Address ? 
                                            <><td>{employee.Address.CityName}</td>
                                            <td>{employee.Address.State}</td>
                                            <td>{employee.Address.Country}</td>
                                            <td>{employee.Address.Pin}</td>
                                            <td>{employee.Address.MobileNo}</td></>
                                        : ''}
                                          <td>
                                            <div>
                                            <button className="btn btn-success" onClick={() => this.editEmployee(employee.EmpId)} style={{marginLeft: '10px'}}> Edit</button>
                                            <button className="btn btn-success" onClick={() => this.deleteEmployee(employee.EmpId)} style={{marginLeft: '10px'}}> Delete</button>
                                            </div>
                                        </td>
                                    </tr>
                                )
                           }
                       </tbody>
                     </table>
            </div>
        );
    }
}
export default ListEmployeeComponent;