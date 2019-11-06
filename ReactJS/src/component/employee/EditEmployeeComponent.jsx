import React, { Component } from 'react'
import ApiService from "../../service/ApiService";
class EditEmployeeComponent extends Component {


    constructor(props){
        super(props);
        this.state ={
            empId: '',
            empName: '',
            companyName: '',
            cityName: '',
            state: '',
            country: '',
            pin:'',
            mobileNo:'',
        }
        this.saveEmployee = this.saveEmployee.bind(this);
        this.loadEmployee = this.loadEmployee.bind(this);
    }
    componentDidMount() {
        this.loadEmployee();
    }

    loadEmployee() {
        ApiService.fetchEmployeeById(window.localStorage.getItem("EmpId"))
            .then((res) => {
                let employee = res.data;
                this.setState({
                EmpId: employee.EmpId,
                EmpName: employee.EmpName,
                CompanyName: employee.CompanyName,
                CityName: employee.Address.CityName,
                State: employee.Address.State,
                Country: employee.Address.Country,
                Pin: employee.Address.Pin,
                MobileNo: employee.Address.MobileNo,
                })
            });
        }
        onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

        saveEmployee = (e) => {
            e.preventDefault();
            let employee = {EmpId: this.state.EmpId, EmpName: this.state.EmpName,CompanyName:this.state.CompanyName,
                Address:{ CityName: this.state.CityName, State: this.state.State, Country: this.state.Country, Pin: this.state.Pin,MobileNo:this.state.MobileNo}};
            ApiService.editEmployee(employee)
                .then(res => {
                    this.setState({message : 'Employee added successfully.'});
                    this.props.history.push('/');
                });
        }
        render() {
            return (
                <div>
                <h2 className="text-center">Edit Employee</h2>
                <form>
                <div className="form-group">
                    <label>Id:</label>
                    <input type="text" placeholder="Employee Id" name="EmpId" className="form-control" value={this.state.EmpId} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>Name:</label>
                    <input type="text" placeholder="Employee Name" name="EmpName" className="form-control" value={this.state.EmpName} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>Company Name:</label>
                    <input type="text" placeholder="Company Name" name="CompanyName" className="form-control" value={this.state.CompanyName} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>City:</label>
                    <input type="text" placeholder="City Name" name="CityName" className="form-control" value={this.state.CityName} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>State:</label>
                    <input type="text" placeholder="State" name="State" className="form-control" value={this.state.State} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>Country:</label>
                    <input type="text" placeholder="Country" name="Country" className="form-control" value={this.state.Country} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>Pin:</label>
                    <input type="text" placeholder="Pin" name="Pin" className="form-control" value={this.state.Pin} onChange={this.onChange}/>
                </div>
                <div className="form-group">
                    <label>Mobile:</label>
                    <input type="text" placeholder="MobileNo" name="MobileNo" className="form-control" value={this.state.MobileNo} onChange={this.onChange}/>
                </div>
                <button className="btn btn-success" onClick={this.saveEmployee}>Save</button>
                 </form>
             </div>
            );
        }
}
export default EditEmployeeComponent;