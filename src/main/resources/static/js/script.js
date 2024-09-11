// Scripts pour afficher des messages d'erreur personnalisés
function validateForm() {
    var username = document.getElementById("register-username").value;
    var email = document.getElementById("register-email").value;
    var password = document.getElementById("register-password").value;

    var usernamePattern = /^[a-zA-Z0-9_-]{3,16}$/;
    var emailPattern = /^[\w\-.]+@([\w\-]+\.)+[\w\-]{2,4}$/;
    var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,}$/;

    if (!username.match(usernamePattern)) {
        alert("Le nom d'utilisateur doit contenir entre 3 et 16 caractères et ne peut inclure que des lettres, chiffres, underscores ou tirets.");
        return false;
    }

    if (!email.match(emailPattern)) {
        alert("Veuillez entrer une adresse email valide.");
        return false;
    }

    if (!password.match(passwordPattern)) {
        alert("Le mot de passe doit contenir au moins 8 caractères, avec au moins une majuscule, une minuscule, un chiffre et un caractère spécial.");
        return false;
    }

    return true;
}

function validateAdForm() {
    // Définir le pattern pour les validations
    var invalidPattern = /^[^<>{}[\]\\\"\'%]+$/;

    // Récupérer les champs à valider
    var fieldsToValidate = [
        document.getElementById("title").value,
        document.getElementById("description").value,
        document.getElementById("location").value,
        document.getElementById("hardwareType").value
    ];

    // Valider les champs non-URL
    for (var i = 0; i < fieldsToValidate.length; i++) {
        if (!fieldsToValidate[i].match(invalidPattern)) {
            alert("L'entrée contient des caractères non autorisés.");
            return false;
        }
    }

    return true;
}

// formater les prix
document.addEventListener("DOMContentLoaded", function() {
        const priceElements = document.querySelectorAll('.ad-price');
        priceElements.forEach(function(el) {
            let price = parseFloat(el.textContent);
            el.textContent = price.toFixed(2) + ' €';
        });
    });


// Configuration des types de cookies
const cookieTypes = {
    essential: { name: 'Essentiels', description: 'Nécessaires au fonctionnement du site', required: true },
    analytics: { name: 'Analytiques', description: 'Pour comprendre l\'utilisation du site' },
    personalization: { name: 'Personnalisation', description: 'Pour un contenu adapté' },
    advertising: { name: 'Publicitaires', description: 'Pour des publicités pertinentes' }
};

// Création du popup
function createCookiePopup() {
    const popup = document.createElement('div');
    popup.id = 'cookiePopup';
    popup.style.cssText = `
        position: fixed; bottom: 20px; left: 20px; width: 300px; background-color: #f1f1f1;
        padding: 15px; box-shadow: 0 0 10px rgba(0,0,0,0.1); display: none;
        font-family: Arial, sans-serif; font-size: 14px; border-radius: 5px;
        z-index: 1000;
    `;

    const content = `
        <h3 style="margin-top: 0; margin-bottom: 10px;">Paramètres des cookies</h3>
        <p style="margin-bottom: 10px;">Nous utilisons des cookies pour améliorer votre expérience. Choisissez vos préférences :</p>
        <div id="cookieOptions" style="margin-bottom: 10px;"></div>
        <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
            <button id="acceptAllCookies" style="padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; font-size: 12px;">Tout accepter</button>
            <button id="savePreferences" style="padding: 5px 10px; background-color: #008CBA; color: white; border: none; cursor: pointer; font-size: 12px;">Enregistrer</button>
            <button id="rejectNonEssential" style="padding: 5px 10px; background-color: #f44336; color: white; border: none; cursor: pointer; font-size: 12px;">Refuser</button>
        </div>
        <a href="/privacy-policy.html" style="color: blue; font-size: 12px;">Politique de confidentialité</a>
    `;

    popup.innerHTML = content;
    document.body.appendChild(popup);

    createCookieOptions();
    addEventListeners();
}

// Création des options de cookies
function createCookieOptions() {
    const container = document.getElementById('cookieOptions');
    for (const [key, value] of Object.entries(cookieTypes)) {
        const div = document.createElement('div');
        div.style.marginBottom = '5px';
        const disabled = value.required ? 'disabled' : '';
        const checked = value.required ? 'checked' : '';
        div.innerHTML = `
            <input type="checkbox" id="${key}" ${disabled} ${checked}>
            <label for="${key}" style="font-size: 12px;">${value.name}</label>
        `;
        container.appendChild(div);
    }
}

// Ajout des écouteurs d'événements
function addEventListeners() {
    document.getElementById('acceptAllCookies').addEventListener('click', () => acceptCookies('all'));
    document.getElementById('savePreferences').addEventListener('click', savePreferences);
    document.getElementById('rejectNonEssential').addEventListener('click', () => acceptCookies('essential'));
}

// Gestion de l'acceptation des cookies
function acceptCookies(level) {
    const preferences = level === 'all'
        ? Object.fromEntries(Object.keys(cookieTypes).map(key => [key, true]))
        : { essential: true };
    setCookie('cookiePreferences', JSON.stringify(preferences), 365);
    hideCookiePopup();
}

// Sauvegarde des préférences
function savePreferences() {
    const preferences = {};
    for (const key of Object.keys(cookieTypes)) {
        preferences[key] = document.getElementById(key).checked;
    }
    setCookie('cookiePreferences', JSON.stringify(preferences), 365);
    hideCookiePopup();
}

// Fonction pour définir un cookie
function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + date.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

// Fonction pour récupérer un cookie
function getCookie(name) {
    const cookieName = name + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    for(let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(cookieName) == 0) {
            return cookie.substring(cookieName.length, cookie.length);
        }
    }
    return "";
}

// Vérification et affichage du popup
function checkCookieConsent() {
    const preferences = getCookie('cookiePreferences');
    if (!preferences) {
        showCookiePopup();
    }
}

// Afficher le popup
function showCookiePopup() {
    document.getElementById('cookiePopup').style.display = 'block';
}

// Cacher le popup
function hideCookiePopup() {
    document.getElementById('cookiePopup').style.display = 'none';
}

// Initialisation
document.addEventListener('DOMContentLoaded', () => {
    createCookiePopup();
    checkCookieConsent();
});