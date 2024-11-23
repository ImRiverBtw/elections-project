<template>

  <div>
    <button class="ShowModalButton" @click="showModalNewPost">
      <span class="plusIcon">+</span>
    </button>
  </div>

  <div v-if="showModal" class="modal-overlay">

    <div class="modal-content">

      <div class="modalTop">
        <h2> Nieuwe Post</h2>
        <hr>
      </div>

      <form>

        <label for="postTitel">Titel</label>
        <input type="text" id="postTitel" name="postTitel" placeholder="Titel" v-model="post.title"><br><br>

        <label for="tags">Tags</label>
        <input type="text" id="tags" name="tags" placeholder="Typ en klik op een tag" v-model="post.tags"><br><br>

        <label for="body">Tekst</label>
        <textarea id="body" name="body" placeholder="Schrijf hier uw tekst" v-model="post.body"></textarea>

      </form>

      <button @click="submitPost" class="submit-button"> Maak post aan</button>
      <button @click="closeModalNewPost" class="close-button"> Annuleer </button>


    </div>
  </div>



</template>

<script>

export default {

  name: "NewPostComponent",
  components: [],

  data() {

    return {

      showModal: false,

      existingTags: ["PVV", "VVD", "PVDA"],
      customTag: "",
      selectedTags: [],

      post: {

        title: "",
        tags: [],
        body: "",

      },
    }

  },

  methods: {

    showModalNewPost() {

      this.showModal = true;

    },

    closeModalNewPost() {

      this.showModal = false;

    },

    submitPost() {

      console.log(this.post)
    },

  },
}

</script>

<style scoped>

.ShowModalButton {
  width: 60px;
  height: 60px;
  background-color: #004494;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.1s linear;
}

.plusIcon {
  font-size: 50px;
  color: white;
  font-weight: bold;
}

.ShowModalButton:hover {
  background-color: #2d71cc;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 750px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  animation: fadeIn 0.3s ease;
}

.modalTop {
  margin-bottom: 10px;
}


.close-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
}

.close-button:hover {
  background-color: #d32f2f;
}

.submit-button {
  background-color: #004494;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-right: 20%;
}

.submit-button:hover {
  background-color: #2d71cc;
}

/* Animation for modal */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

hr{
  margin: 10px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
  text-align: left;
}

input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  box-sizing: border-box;
}

textarea {
  resize: vertical;
}

#body {
  height: 250px;
}

</style>