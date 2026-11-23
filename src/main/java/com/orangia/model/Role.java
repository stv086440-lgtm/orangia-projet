package com.orangia.model;

/**
 * Rôles prédéfinis et statiques des utilisateurs ORANGIA.
 *
 * Les permissions ne sont pas définies ici : elles seront gérées
 * par les couches Service et Security.
 */
public enum Role {

    ADMINISTRATEUR,
    RESPONSABLE,
    RH,
    COMPTABLE
}
