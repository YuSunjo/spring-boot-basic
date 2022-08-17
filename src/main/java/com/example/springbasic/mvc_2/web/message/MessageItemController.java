package com.example.springbasic.mvc_2.web.message;

import com.example.springbasic.mvc_2.domain.message.Item;
import com.example.springbasic.mvc_2.domain.message.MessageItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/message/items")
@RequiredArgsConstructor
public class MessageItemController {

    private final MessageItemRepository messageItemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = messageItemRepository.findAll();
        model.addAttribute("items", items);
        return "message/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = messageItemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "message/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "message/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = messageItemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/message/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = messageItemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "message/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        messageItemRepository.update(itemId, item);
        return "redirect:/message/items/{itemId}";
    }

}

