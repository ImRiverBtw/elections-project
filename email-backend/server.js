const express = require('express');
const cors = require('cors'); // Include CORS middleware
const bodyParser = require('body-parser');
const { sendEmail } = require('./emailService');
const app = express();
const port = 3000;

// Enable CORS for requests from your frontend
app.use(cors({
    origin: 'http://127.0.0.1:5173', // Replace with your frontend's exact URL
    methods: ['GET', 'POST', 'OPTIONS'], // Allowed methods
    allowedHeaders: ['Content-Type'], // Allowed headers
}));

app.use(bodyParser.json());

app.post('/send-email', async (req, res) => {
    const { to, subject, text } = req.body;

    try {
        const info = await sendEmail(to, subject, text);
        console.log('Email sent:', info?.messageId || 'No messageId returned');

        res.status(200).json({ success: true, messageId: info?.messageId || 'Email sent successfully' });
    } catch (error) {
        console.error('Error sending email:', error);
        res.status(500).json({ success: false, error: error.message });
    }
});

app.listen(port, () => {
    console.log(`Server running on http://localhost:${port}`);
});
