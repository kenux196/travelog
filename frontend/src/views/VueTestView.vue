<template>
  <div class="test">
    <h1>This is Vue.js Test Page</h1>
    <br />
    <b-button>Button</b-button>
    <b-button variant="danger" @click="showMessage">Show Message</b-button>
    <b-button variant="success">Button</b-button>
    <b-button variant="outline-primary">Button</b-button>
    <div class="p-3 text-primary-emphasis bg-primary-subtle border border-primary-subtle rounded-3">
      Example element with utilities
    </div>
  </div>
</template>
<script>
export default {
  name: 'VueTestView',
  data() {
    return {
      message: '위험',
    };
  },
  methods: {
    showMessage() {
      alert(this.message);
    },
  },
};
</script>
