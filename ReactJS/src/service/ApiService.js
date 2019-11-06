import axios from 'axios';

const USER_API_BASE_URL = 'http://localhost:9090/employees';

class ApiService {

    fetchEmployees() {
        return axios.get(USER_API_BASE_URL+'/fetch/fetchAll');
    }
    fetchEmployeeById(empId) {
        return axios.get(USER_API_BASE_URL + '/retrive/' + empId);
    }
    addEmployee(employee) {
        return axios.post(USER_API_BASE_URL+'/create/', employee);
    }
    editEmployee(employee) {
        return axios.put(USER_API_BASE_URL + '/update/' + employee.EmpId, employee);
    }
    deleteEmployee(empId) {
        return axios.delete(USER_API_BASE_URL + '/delete/' + empId);
    }
}

export default new ApiService();
