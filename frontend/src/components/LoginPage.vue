<template>
    <div>
      <h2>Login</h2>
      <form @submit.prevent="login">
        <input type="text" v-model="username" placeholder="Username">
        <input type="password" v-model="password" placeholder="Password">
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        username: '',
        password: ''
      };
    },
    methods: {
      async login() {
        try {
          const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
              username: this.username,
              password: this.password
            })
          });
          if (response.ok) {
            const data = await response.json();
            console.log('Authentication successful:', data);
          } else {
            console.error('Authentication failed');
          }
        } catch (error) {
          console.error('Error during login:', error);
        }
      }
    }
  };
  </script>