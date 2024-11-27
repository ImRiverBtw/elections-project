const nodemailer = require('nodemailer');

// Gmail SMTP configuration
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'hteam1965@gmail.com', // Your Gmail address
        pass: 'byitxigeqplnsazo',    // The App Password
    },
});

/**
 * Send an email with a dynamic reset link.
 * @param {string} to - Recipient's email address
 * @param {string} subject - Subject of the email
 * @param {string} text - Text body of the email
 * @param {string} resetLink - Password reset link
 */
const sendEmail = async (to, subject, text, resetLink) => {
    const mailOptions = {
        from: 'hteam1965@gmail.com', // Sender address
        to,                          // Recipient address
        subject,                     // Subject of the email
        text: `${text}\nReset your password using this link: ${resetLink}`, // Include reset link in plain text
        html: `<p>${text}</p><p><a href="${resetLink}" target="_blank">Reset your password</a></p>`, // HTML version with clickable link
    };

    try {
        const info = await transporter.sendMail(mailOptions);
        console.log('Email sent:', info.messageId);
        return { success: true, messageId: info.messageId };
    } catch (error) {
        console.error('Error sending email:', error);
        return { success: false, error: error.message };
    }
};

module.exports = { sendEmail };
