package com.furniturear;

import android.net.Uri;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class Model {
    private ModelRenderable postRenderable;
    private String fileName;

    Model(String s){
        fileName = s+".sfb";
    }

    //перезагружает модель
    void resetModelRenderable(MainActivity activity){
        ModelRenderable.builder()
                .setSource(activity, Uri.parse(fileName)) //что
                .build() //собираем
                .thenAccept(renderable -> postRenderable = renderable)//сохраним
                .exceptionally(throwable -> { Toast toast = //в случае ошибки
                        Toast.makeText(activity, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });
    }

    //настраивает размещаемую модель
    void setOnTapListener(ArFragment fragment){
        fragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
                    if (postRenderable == null){
                        return;
                    }

                    Anchor anchor = hitresult.createAnchor(); //создаём привязочный якорь
                    AnchorNode anchorNode = new AnchorNode(anchor); //опорный узел
                    anchorNode.setParent(fragment.getArSceneView().getScene());

                    TransformableNode model = new TransformableNode(fragment.getTransformationSystem());//Относительный узел
                    model.setParent(anchorNode);  //разница в том, что его можно передвигать, вращать и тд
                    model.setRenderable(postRenderable);
                    model.select();
                }
        );
    }
}
