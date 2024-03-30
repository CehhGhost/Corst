<script>
import { Recogito } from "@recogito/recogito-js";
import "@recogito/recogito-js/dist/recogito.min.css";

export default {
  data() {
    return {
      recogito: null,
    };
  },
  mounted() {
    this.recogito = new Recogito({
      content: "recogito",
      mode: "annotate",
      selectHandler: (annotation) => {
        console.log("Selected:", annotation);
      },
      createAnnotationHandler: (annotation) => {
        console.log("Created:", annotation);
      },
      updateAnnotationHandler: (annotation) => {
        console.log("Updated:", annotation);
      },
      deleteAnnotationHandler: (annotation) => {
        console.log("Deleted:", annotation);
      },
    });
  },
  beforeUnmount() {
    if (this.recogito) {
      this.recogito.destroy();
    }
  },
};
</script>

<template>
  <div>
    <div ref="recogitoContainer" style="height: 500px" id="recogito">
      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam viverra
        urna vitae orci sagittis, ac tempus ligula ultrices. Mauris non ipsum eu
        lectus vehicula vulputate id ac tortor. In et ligula in libero vulputate
        egestas. Fusce rutrum non risus nec feugiat. Integer quis lacus
        malesuada, congue dolor sit amet, condimentum libero.
      </p>

      <p>
        Donec tincidunt urna libero, sit amet fermentum justo auctor vel. Ut
        sodales lorem id semper maximus. Fusce dapibus arcu quis justo ultrices,
        et consequat velit placerat. Pellentesque habitant morbi tristique
        senectus et netus et malesuada fames ac turpis egestas. Proin feugiat
        nunc a turpis ullamcorper molestie. Praesent vitae sollicitudin libero,
        nec molestie odio. Suspendisse potenti. Vivamus non tincidunt libero.
        Cras non dui mi.
      </p>
    </div>
  </div>
</template>
