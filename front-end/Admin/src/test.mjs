import axios from 'axios';

axios.get('http://localhost:9090/api/v1/contacts')
    .then(response => console.log(response.data))
    .catch(error => console.error('Error:', error));