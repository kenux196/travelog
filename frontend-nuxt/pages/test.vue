<template>
  <div>nuxt test page</div>
  <NuxtLink to="/">Go to main</NuxtLink>
  <div>
    Counter: {{ counter }}
    <button @click="counter++">+</button>
    <button @click="counter--">-</button>
  </div>
  <p>Current color: {{ color }}</p>
  <p>Current user: {{ name }}</p>
</template>

<script setup>
// const counter = useState('counter', () => Math.round(Math.random() * 1000))
const color = useColor()
const counter = useCounter()
const name = useName();
</script>
