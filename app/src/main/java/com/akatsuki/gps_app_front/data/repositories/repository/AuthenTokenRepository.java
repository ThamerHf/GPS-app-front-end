package com.akatsuki.gps_app_front.data.repositories.repository;

import android.util.Log;

import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.repositories.dao.AuthenTokenDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthenTokenRepository {

    private final AuthenTokenDao authenTokenDao;

    public AuthenTokenRepository(AuthenTokenDao authenTokenDao) {
        this.authenTokenDao = authenTokenDao;
    }

    public void getAllToken(final AppCallback<List<AuthenToken>> callback) {
        authenTokenDao.getAllToken().subscribeOn(Schedulers.io()) // Spécifie l'exécution sur un thread d'entrée-sortie
            .observeOn(AndroidSchedulers.mainThread()) // Spécifie l'observation sur le thread principal
            .subscribe(new SingleObserver<List<AuthenToken>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    // Mise en œuvre facultative de cette méthode si nécessaire
                }

                @Override
                public void onSuccess(List<AuthenToken> authenToken) {
                    callback.onLoginSuccess(authenToken);
                }

                @Override
                public void onError(Throwable e) {
                    callback.onLoginError(new IOException("update token failed"));
                }
            }
        );
    }

    public void getAuthToken(final AppCallback<AuthenToken> callback) {
        authenTokenDao.getAllToken().subscribeOn(Schedulers.io()) // Spécifie l'exécution sur un thread d'entrée-sortie
            .observeOn(AndroidSchedulers.mainThread()) // Spécifie l'observation sur le thread principal
            .subscribe(new SingleObserver<List<AuthenToken>>() {
               @Override
               public void onSubscribe(Disposable d) {
                   // Mise en œuvre facultative de cette méthode si nécessaire
               }

               @Override
               public void onSuccess(List<AuthenToken> authenToken) {
                   if (!authenToken.isEmpty()) {
                       callback.onLoginSuccess(authenToken.get(0));
                   }
               }

               @Override
               public void onError(Throwable e) {
                   callback.onLoginError(new IOException("update token failed"));
               }
            }
        );
    }

    public void updateToken(AuthenToken authenToken) {
        authenTokenDao.updateToken(authenToken)
        .subscribeOn(Schedulers.io()) // spécifie l'exécution sur un thread d'E/S
        .observeOn(AndroidSchedulers.mainThread()) // spécifie l'observation sur le thread principal
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                // Mise en œuvre facultative de cette méthode si nécessaire
            }

            @Override
            public void onComplete() {
                Log.d("Update Token", "Token updated");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Update Token Failed", "Failed token update");
            }
        });
    }

    public void addToken(AuthenToken authenToken) {
        authenTokenDao.addToken(authenToken)
        .subscribeOn(Schedulers.io()) // spécifie l'exécution sur un thread d'E/S
        .observeOn(AndroidSchedulers.mainThread()) // spécifie l'observation sur le thread principal
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                // Mise en œuvre facultative de cette méthode si nécessaire
            }

            @Override
            public void onComplete() {
                Log.d("Add Token", "Token Added");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Add Token Failed", "Failed token Add");
            }
        });
    }

    public void deleteToken(AuthenToken authenToken) {
        authenTokenDao.deleteToken(authenToken)
        .subscribeOn(Schedulers.io()) // spécifie l'exécution sur un thread d'E/S
        .observeOn(AndroidSchedulers.mainThread()) // spécifie l'observation sur le thread principal
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                // Mise en œuvre facultative de cette méthode si nécessaire
            }

            @Override
            public void onComplete() {
                Log.d("Delete Token", "Token deleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Delete Token Failed", "Failed token delete");
            }
        });
    }

}
