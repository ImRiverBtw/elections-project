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
        <input
            type="text"
            id="postTitel"
            name="postTitel"
            placeholder="Titel"
            v-model="post.title"
            :class="{'border-red': showError.title}"
            @input="clearError('title')"><br><br>

        <label for="tags">Tags</label>

<!--        zelf een tag aanmaken -->
<!--        <input-->
<!--            type="text"-->
<!--            id="tags"-->
<!--            name="tags"-->
<!--            placeholder="Typ en klik op een tag"-->
<!--            v-model="post.tags"-->
<!--            :class="{'border-red': showError.tags}"-->
<!--            @input="clearError('tags')"><br><br>-->

        <div class="dropdown">

          <button @click.prevent="toggleDropdown" class="dropdown-button">
            {{ selected || 'Klik op een Tag' }}
          </button>

          <!-- Dropdown Menu -->
          <ul v-if="isOpen" class="dropdown-menu">
            <li
                v-for="(tag, index) in existingTags"
                :key="index"
                @click="selectOption(tag.name)"
                class="dropdown-item"
            >
              {{ tag.name }}
            </li>
          </ul>

        </div>

        <label for="body">Tekst</label>

        <textarea
            id="body"
            name="body"
            placeholder="Schrijf hier uw tekst"
            v-model="post.body"
            :class="{'border-red': showError.body}"
            @input="clearError('body')"></textarea>

      </form>

      <button @click="handleSubmitPost" class="submit-button"> Maak post aan</button>
      <button @click="closeModalNewPost" class="close-button"> Annuleer </button>


    </div>
  </div>



</template>

<script>

import {ref} from "vue";

// const showModal = ref(false)

export default {

  name: "NewPostComponent",
  components: [],

  data() {

    return {

      showModal: false,

      showError: {
        title: false,
        tags: false,
        body: false,
      },

      post: {
        id: null,
        title: "",
        tags: {},
        body: "",
      },

      existingTags: {},

      isOpen: false, // Controls visibility of dropdown
      selected: null // Selected option

    }

  },

  methods: {

    showModalNewPost() {

      this.insertExistingTags();
      this.showModal = true;

    },

    closeModalNewPost() {

      this.showModal = false;
      this.clearInputFields()

    },


    handleSubmitPost() {

      if (this.isValid()) {

        this.submitPost();

      } else {

      }

    },

    async submitPost() {


      const postData = {

        // attributes need to match columns in db //
        title: this.post.title,
        tag: this.selected,

        // needs to change to get userID with jwt token //
        author: "1",

        textContent: this.post.body,

      };

      try {

        const response = await fetch('http://localhost:8080/forumNewPost', {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(postData),
        });

        if (!response.ok) {
          const errorText = await response.text();
          alert("Error: " + errorText);
        } else ("Post aanmaken succesvol");
        {

          this.clearInputFields()

        }
      } catch (error) {
        console.error('Post aanmaken niet gelukt', error);
      }
    },


    clearInputFields() {

      this.post.title = "";
      this.post.body = "";
      this.post.tags = "";

      this.showModal = false;

      this.selected = null;
    },

    isValid() {

      if (this.post.title === "" || this.post.body === "" || this.post.tags === null) {
        console.log("alle velden moeten ingevuld zijn")

        this.showError.title = this.post.title.trim() === '';
        this.showError.tags = this.post.tags.trim() === '';
        this.showError.body = this.post.body.trim() === '';

        return false;
      } else {
        return true;
      }
    },

    clearError(field) {
      this.showError[field] = false;
    },

    async insertExistingTags() {

      try {

        const response = await fetch('http://localhost:8080/forum/tags', {
          method: 'GET',
          headers: {'Content-Type': 'application/json'},
        });

        if (!response.ok) {
          const errorText = await response.text();
          alert("Error: " + errorText);
        } else ("Tags ophalen succesvol ");
        {

          const data = await response.json()
          console.log(data)

          this.existingTags = data;

        }
      } catch (error) {
        console.error('Tags ophalem niet gelukt', error);
      }
    },

      toggleDropdown() {
        this.isOpen = !this.isOpen;
      },
      selectOption(option) {
        this.selected = option;
        this.isOpen = false;
      }
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

.border-red {
  border: 2px solid red;
}
.dropdown {
  position: relative;
  display: inline-block;
}
.dropdown-button {
  padding: 10px;
  background-color: #004594;
  color: white;
  border: none;
  cursor: pointer;
}
.dropdown-menu {
  position: absolute;
  background-color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  list-style: none;
  padding: 0;
  margin: 5px 0 0;
  width: 100%;
  border: 1px solid #ccc;
}
.dropdown-item {
  padding: 10px;
  cursor: pointer;
}
.dropdown-item:hover {
  background-color: #f1f1f1;
}

</style>