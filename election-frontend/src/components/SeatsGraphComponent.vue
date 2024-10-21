<template>
  <div class="half-doughnut">
    <svg
        :width="size"
        :height="size / 2"
        viewBox="0 0 100 50"
    >
      <!-- Background Circle (Full half-doughnut) -->
      <path
          d="M 10 50 A 40 40 0 1 1 90 50 Z"
          fill="#BDBDBD"
      />

      <!-- Foreground Circles (Data segments) -->
      <path
          v-for="(segment, index) in segments"
          :key="index"
          :d="calculateSegmentPath(segment.startAngle, segment.endAngle)"
          :fill="segment.color"
      />
    </svg>
    <div class="label" v-if="showLabel">
      Total: {{ totalValue }} / {{ max }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'HalfDoughnut',
  props: {
    data: {
      type: Array,
      required: true,
      // Each segment should have a 'value' and 'color' property
      default: () => [],
    },
    max: {
      type: Number,
      default: 150, // The maximum value representing the full half-doughnut
    },
    size: {
      type: Number,
      default: 200, // Size of the SVG (width and height)
    },
    showLabel: {
      type: Boolean,
      default: true, // Optionally show the total value label
    },
  },
  computed: {
    // Calculate total value from the data
    totalValue() {
      return this.data.reduce((sum, segment) => sum + segment.value, 0);
    },
    // Calculate each segment's start and end angles
    segments() {
      let cumulativeValue = 0;
      return this.data.map((segment) => {
        const startAngle = (cumulativeValue / this.max) * 180;
        cumulativeValue += segment.value;
        const endAngle = (cumulativeValue / this.max) * 180;
        return {
          ...segment,
          startAngle,
          endAngle,
        };
      });
    },
  },
  methods: {
    // Calculate SVG path for a given segment's start and end angles
    calculateSegmentPath(startAngle, endAngle) {
      const startRadians = (startAngle * Math.PI) / 180;
      const endRadians = (endAngle * Math.PI) / 180;

      const startX = 50 + 40 * Math.cos(startRadians);
      const startY = 50 - 40 * Math.sin(startRadians);
      const endX = 50 + 40 * Math.cos(endRadians);
      const endY = 50 - 40 * Math.sin(endRadians);

      const largeArcFlag = endAngle - startAngle > 180 ? 1 : 0;

      return `M 50 50 L ${startX} ${startY} A 40 40 0 ${largeArcFlag} 1 ${endX} ${endY} Z`;
    },
  },
  watch: {
    // Watch for changes in the data and update segments reactively
    data: {
      handler() {
        this.segments; // Recompute segments when data changes
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
.half-doughnut {
  position: relative;
  display: inline-block;
}
.label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 1.5em;
  font-weight: bold;
  color: #333;
}
</style>
